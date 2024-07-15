package org.javacs.kt

import org.hamcrest.Matchers.*
import org.javacs.kt.classpath.defaultClassPathResolver
import org.javacs.kt.classpath.findKotlinStdlib
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import java.nio.file.Files

class ClassPathTest {
    companion object {
        @JvmStatic
        @BeforeClass
        fun setupLogger() {
            LOG.connectStdioBackend()
        }
    }

    @Test
    fun `find gradle classpath`() {
        val workspaceRoot = testResourcesRoot().resolve("additionalWorkspace")
        val buildFile = workspaceRoot.resolve("build.gradle")

        assertTrue(Files.exists(buildFile))

        val resolvers = defaultClassPathResolver(listOf(workspaceRoot))
        print(resolvers)
        val classPath = resolvers.classpathOrEmpty.map { it.toString() }

        assertThat(classPath, hasItem(containsString("junit")))
    }

    @Test
    fun `find maven classpath`() {
        val workspaceRoot = testResourcesRoot().resolve("mavenWorkspace")
        val buildFile = workspaceRoot.resolve("pom.xml")

        assertTrue(Files.exists(buildFile))

        val resolvers = defaultClassPathResolver(listOf(workspaceRoot))
        print(resolvers)
        val classPath = resolvers.classpathOrEmpty.map { it.toString() }

        assertThat(classPath, hasItem(containsString("junit")))
    }

    @Test
    fun `find kotlin stdlib`() {
        assertThat(findKotlinStdlib(), notNullValue())
    }
}
