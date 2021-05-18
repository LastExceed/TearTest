//kgl commonization pl0x

expect object GL {
	fun clearColor(r: Float, g: Float, b: Float, a: Float)
	fun color3f(r: Float, g: Float, b: Float)
	fun viewport(x: Int, y: Int, width: Int, height: Int)
	fun clear(mask: Int)
	fun begin(mode: Int)
	fun vertex2f(x: Float, y: Float)
	fun end()

	fun createCapabilities()

	val COLOR_BUFFER_BIT: Int
	val LINES: Int
}