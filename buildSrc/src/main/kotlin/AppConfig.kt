
object AppConfig {

    const val MIN_SDK = 21
    const val TARGET_SDK = 29

    object BuildTypes {

        val DEV = AppBuildType("dev")
        val DEBUG = AppBuildType("debug")
        val RELEASE = AppBuildType("release")
    }

}