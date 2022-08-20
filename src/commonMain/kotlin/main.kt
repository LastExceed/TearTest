import com.kgl.glfw.*

fun main() {
	Glfw.init()

	Window(800, 600, "TearTest", null).run {
		Glfw.currentContext = this
		Glfw.setSwapInterval(0) //disable vsync
		GL.createCapabilities()

		GL.clearColor(0f, 0f, 0f, 1f)
		GL.color3f(1f, 1f, 1f)

		setKeyCallback { _, key, _, action, _ ->
			if (action != Action.Press) return@setKeyCallback

			when (key) {
				KeyboardKey.F11 -> {
					Glfw.primaryMonitor!!.run {
						val (monitor, width, height) =
							if (monitor == null)
								Triple(this, videoMode.width, videoMode.height)
							else
								Triple(null, 800, 600)

						setMonitor(monitor, 100, 100, width, height, videoMode.refreshRate)
					}
				}

				else -> {}
			}
		}

		setSizeCallback { _, width, height ->
			GL.viewport(0, 0, width, height)
		}

		setCursorPosCallback { _, x, y ->
			val xf = (x.toFloat() / size.first) * 2f - 1f

			GL.clear(GL.COLOR_BUFFER_BIT)

			GL.begin(GL.LINES)
			GL.vertex2f(xf, 1f)
			GL.vertex2f(xf, -1f)
			GL.end()

			swapBuffers()
		}

		while (!shouldClose) Glfw.waitEvents()
	}
}