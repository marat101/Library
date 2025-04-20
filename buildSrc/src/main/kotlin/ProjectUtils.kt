import org.gradle.api.Project
import java.util.Properties

fun Project.getProperty(key: String): String {
    val localProperties = Properties().apply {
        rootProject.file("local.properties").takeIf { it.exists() }?.inputStream()?.use { load(it) }
    }

    return localProperties[key]?.toString()?: properties[key]?.toString()!!
}