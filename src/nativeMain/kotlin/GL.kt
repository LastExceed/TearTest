import com.kgl.opengl.glClear
import com.kgl.opengl.glClearColor
import com.kgl.opengl.glViewport
import platform.opengl32.*

actual object GL {
	actual fun clearColor(r: Float, g: Float, b: Float, a: Float) = glClearColor(r,g,b,a)
	actual fun color3f(r: Float, g: Float, b: Float) = glColor3f(r,g,b)
	actual fun viewport(x: Int, y: Int, width: Int, height: Int) = glViewport(x, y, width, height)
	actual fun clear(mask: Int) = glClear(mask.toUInt())
	actual fun begin(mode: Int) = glBegin(mode.toUInt())
	actual fun vertex2f(x: Float, y: Float) = glVertex2f(x, y)
	actual fun end() = glEnd()

	actual fun createCapabilities() {}

	actual val COLOR_BUFFER_BIT = GL_COLOR_BUFFER_BIT
	actual val LINES = GL_LINES
}