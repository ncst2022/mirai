/*
 * Copyright 2019-2022 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

package net.mamoe.mirai.console.plugin.jvm

import java.io.File

/**
 * [JvmPlugin] 的类路径
 *
 * @since 2.12
 * @see AbstractJvmPlugin.jvmPluginClasspath
 */
public interface JvmPluginClasspath {
    public val pluginFile: File

    /**
     * 插件本体的 [ClassLoader], 负责加载插件本身的类数据
     *
     * JVM NAME: `${pluginFile.name}`
     */
    public val pluginClassLoader: ClassLoader

    /**
     * 插件所使用的依赖, 可共享, 当其他插件依赖于当前插件时, 其他插件可以从此 [ClassLoader] 搜索类引用
     *
     * JVM NAME: ${pluginFile.name}&#91;shared]
     */
    public val pluginSharedLibrariesClassLoader: ClassLoader

    /**
     * 插件内部使用的依赖, 不公开给其他插件, 其他插件搜索时不会搜索到此插件的依赖
     *
     * JVM NAME: ${pluginFile.name}&#91;private]
     */
    public val pluginIndependentLibrariesClassLoader: ClassLoader

    /**
     * 将 [file] 加入 [classLoader] 的搜索路径内
     *
     * @throws IllegalArgumentException 当 [classLoader] 不是 [pluginClassLoader],
     * [pluginIndependentLibrariesClassLoader], [pluginSharedLibrariesClassLoader] 时抛出
     */
    @kotlin.jvm.Throws(IllegalArgumentException::class)
    public fun addToPath(classLoader: ClassLoader, file: File)

    /**
     * 下载依赖, 并注册进 [classLoader]
     *
     * 注: 如果需要同时修改 [pluginSharedLibrariesClassLoader] 和 [pluginIndependentLibrariesClassLoader],
     * 请先对 [pluginSharedLibrariesClassLoader] 以避免出现不和预期的问题
     *
     * @throws IllegalArgumentException 当 [classLoader] 不是
     * [pluginSharedLibrariesClassLoader], [pluginIndependentLibrariesClassLoader]
     * 时抛出
     * @throws Exception 网络错误 / 依赖未找到
     */
    @kotlin.jvm.Throws(IllegalArgumentException::class, Exception::class)
    public fun downloadAndAddToPath(classLoader: ClassLoader, dependencies: Collection<String>)
}
