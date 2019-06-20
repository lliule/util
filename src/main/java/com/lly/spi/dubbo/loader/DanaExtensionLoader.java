package com.lly.spi.dubbo.loader;

import com.lly.spi.dubbo.annotation.DanaSpi;
import com.lly.spi.dubbo.util.Holder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

/**
 *
 * @author leliu
 * @date 2019/6/20 11:55
 */
public class DanaExtensionLoader<T> {
	/**
	 * 定义spi文件的扫描路径前缀
	 */
	private static final String DANA_DIRECTORY = "META-INF/dana/";

	/**
	 * 分割spi上默认扩展点字符串用的
	 */
	private static  final Pattern NAME_SEPARETOR = Pattern.compile("\\s*[,]+\\s");

	/**
	 * 扩展点加载器的缓存
	 */
	private static final ConcurrentMap<Class<?>, DanaExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap();

	/**
	 * 扩展点的缓存
	 */
	private static final ConcurrentMap<Class<?>, Object> EXTENSION_INSTANCE = new ConcurrentHashMap<>();

	/**
	 * 接口的class
	 */
	private final Class<?> type;


	/**
	 * 接口SPI默认的实现名（就是接口的注解上填的默认值)
	 */
	private String cachedDefaultName;

	/**
	 * 异常记录
	 */
	private Map<String, IllegalStateException> exceptions = new ConcurrentHashMap<>();

	private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

	private final ConcurrentMap <String, Holder<Object>> cacheInstances = new ConcurrentHashMap<>();

	private DanaExtensionLoader(Class<?> type) {
		this.type = type;
	}

	public static <T> DanaExtensionLoader<T> getExtensionLoader(Class<T> type) {
		if(type == null) {
			throw new IllegalArgumentException("Extension type == null");
		}
		if(!type.isInterface()) {
			throw new IllegalArgumentException("Extension type (" + type + ")is not interface!");
		}
		if(!withExtensionAnnotation(type)) {
			throw new IllegalArgumentException("Extension type(" +type + ") is not extension, because WITHOUT @" + DanaSpi.class.getSimpleName() + " Annotation!");
		}

		// 从缓存中获取加载器，如果不存在就新建后加入缓存
		DanaExtensionLoader<T> extensionLoader = (DanaExtensionLoader<T>) EXTENSION_LOADERS.get(type);
		if(extensionLoader == null) {
			EXTENSION_LOADERS.putIfAbsent(type, new DanaExtensionLoader<>(type));
			extensionLoader = (DanaExtensionLoader) EXTENSION_LOADERS.get(type);
		}
		return extensionLoader;
	}

	private static <T> boolean withExtensionAnnotation(Class<T> type) {
		return type.isAnnotationPresent(DanaSpi.class);
	}


	public T getExtension(String name) {
		if(name == null || name.length() == 0) {
			throw new IllegalArgumentException("Extension name == null");
		}

		if("true".equals(name)) {
			return getDefaultExtension();
		}

		Holder<Object> holder = cacheInstances.get(name);
		if(holder == null) {
			cacheInstances.putIfAbsent(name, new Holder<>());
			holder = cacheInstances.get(name);
		}

		Object instance = holder.getValue();
		if(instance == null) {
			synchronized (holder) {
				if(instance == null) {
					instance = createExtension(name);
					holder.setValue(instance);
				}
			}
		}

		return (T) instance;
	}

	/**
	 * 获取到接口实现的class实例化对象返回
	 * @param name name
	 * @return
	 */
	private T createExtension(String name) {

		Class<?> clazz = getExtensionClasses().get(name);
		if(clazz == null) {
			throw findException(name);
		}

		try {
			T instance = (T) EXTENSION_INSTANCE.get(clazz);
			if(instance == null) {
				EXTENSION_INSTANCE.putIfAbsent(clazz, (T) clazz.newInstance());
				instance = (T) EXTENSION_INSTANCE.get(clazz);
			}
			return instance;
		} catch (Exception e) {
			throw new IllegalStateException("Extension instance(name: " + name + ", class: " +
					type + ")  could not be instantiated: " + e.getMessage(), e);
		}
	}


	private Map<String, Class<?>> getExtensionClasses() {
		Map<String, Class<?>> classes = cachedClasses.getValue();
		if(classes == null) {
			synchronized (cachedClasses) {
				if(classes == null) {
					classes = loadExtensionClasses();
					cachedClasses.setValue(classes);
				}
			}
		}
		return classes;
	}

	private Map<String, Class<?>> loadExtensionClasses() {
		DanaSpi annotation = type.getAnnotation(DanaSpi.class);
		if(annotation != null) {
			String value = annotation.value();
			if(value != null && (value = value.trim()).length() > 0) {
				String[] names = NAME_SEPARETOR.split(value);

				if(names.length > 1) {
					throw new IllegalArgumentException("more than 1 default extension name on extension " + type.getName());
				}
				if(names.length == 1) {
					cachedDefaultName = names[0];
				}
			}

		}
		HashMap<String, Class<?>> extensionClasses = new HashMap<>();
		loadFile(extensionClasses, DANA_DIRECTORY);
		return extensionClasses;

	}

	private void loadFile(HashMap<String, Class<?>> extensionClasses, String dir) {
		String fullName = dir + type.getName();
		try {
			Enumeration<URL> urls;
			ClassLoader classLoader = DanaExtensionLoader.class.getClassLoader();

			if(classLoader != null) {
				urls = classLoader.getResources(fullName);
			} else {
				urls = ClassLoader.getSystemResources(fullName);
			}

			if(urls != null) {
				while (urls.hasMoreElements()) {
					URL url = urls.nextElement();

					BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));

					String line = null;
					try {
						while ((line = reader.readLine()) != null) {
							// 去掉本行注释的信息
							final int ci = line.indexOf('#');
							if(ci >= 0) {
								line = line.substring(0, ci);
							}
							line = line.trim();
							if(line.length() > 0) {
								String name = null;
								// 截取 = ,将 =前的key作为key， 将 =后的value作为v
								int i = line.indexOf('=');
								if(i > 0) {
									name = line.substring(0, i).trim();
									line = line.substring(i + 1).trim();
								}
								if(line.length() > 0) {
									Class<?> clazz = Class.forName(line, true, classLoader);
									if(!type.isAssignableFrom(clazz)) {
										throw new IllegalArgumentException("error when load extension class (interfase:" +
										type + ", class line: " + clazz.getName() + "), class " + clazz.getName() + " is not subtype of interface.");
									}
									extensionClasses.put(name, clazz);
								}
							}
						}

					} catch (Exception e) {
						IllegalStateException exception = new IllegalStateException("error when load extension class (interfase:" +
								type + ", class line: " + type + ", class " + line + ") in " + url + ",cause: " + e.getMessage(), e);

						exceptions.put(line, exception);
					}finally {
						reader.close();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private T getDefaultExtension() {
		getExtensionClasses();
		if(null == cachedDefaultName || cachedDefaultName.length() == 0 || "true".equals(cachedDefaultName)) {
			return null;
		}

		return getExtension(cachedDefaultName);
	}


	//异常提示
	private IllegalStateException findException(String name) {
		for (Map.Entry<String, IllegalStateException> entry : exceptions.entrySet()) {
			if (entry.getKey().toLowerCase().contains(name.toLowerCase())) {
				return entry.getValue();
			}
		}
		StringBuilder buf = new StringBuilder("No such extension " + type.getName() + " by name " + name);

		int i = 1;
		for (Map.Entry<String, IllegalStateException> entry : exceptions.entrySet()) {
			if (i == 1) {
				buf.append(", possible causes: ");
			}

			buf.append("\r\n(");
			buf.append(i++);
			buf.append(") ");
			buf.append(entry.getKey());
			buf.append(":\r\n");
			buf.append(entry.getValue().toString());
		}
		return new IllegalStateException(buf.toString());
	}

}
