import cglfw.*
import com.kgl.glfw.*
import com.kgl.opengl.*
import com.kgl.opengl.GL_COLOR_BUFFER_BIT
import com.kgl.opengl.GL_LINES
import com.kgl.opengl.glClear
import com.kgl.opengl.glClearColor
import com.kgl.opengl.glViewport
import com.kgl.opengl.utils.*
import platform.opengl32.*

fun main() {
	Glfw.init()

	val window = Window(800, 600, "", null)

	Glfw.currentContext = window
	Glfw.setSwapInterval(0) //disable vsync

	//Loader.load()

	glClearColor(0f, 0f, 0f, 1f)
	glColor3f(1f, 1f, 1f)

	window.setKeyCallback { _, key, scancode, action, mods ->
		if (key == KeyboardKey.F11 && action == Action.Press) {
			with(Glfw.primaryMonitor!!) {
				val monitor = if (window.monitor == null) this else null
				//https://github.com/Dominaezzz/kgl/issues/33
				//window.setMonitor(monitor, 0, 0, videoMode.width, videoMode.height, videoMode.refreshRate)
				glfwSetWindowMonitor(window.ptr, monitor?.ptr, 0, 0, videoMode.width, videoMode.height, videoMode.refreshRate)
			}
		}
	}

	window.setSizeCallback { _, width, height ->
		glViewport(0, 0, width, height)
	}

	window.setCursorPosCallback {_, x, y ->
		val xf = (x.toFloat() / window.size.first) * 2f - 1f

		glClear(GL_COLOR_BUFFER_BIT)

		glBegin(GL_LINES)
		glVertex2f(xf, 1f)
		glVertex2f(xf, -1f)
		glEnd()

		window.swapBuffers()
	}

	while (!window.shouldClose) Glfw.pollEvents()
}