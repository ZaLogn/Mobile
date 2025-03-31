plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ui_loginregister"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ui_loginregister"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Retrofit core
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // Converter để xử lý JSON (dùng Gson)
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // (Tùy chọn) Interceptor để debug request và response
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
    //load ảnh với Glide
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")
    implementation ("com.squareup.picasso:picasso:2.5.2")
}