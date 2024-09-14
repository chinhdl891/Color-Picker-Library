plugins {
    id("com.android.library")
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.chinchin.palletview"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    extensions.getByType(PublishingExtension::class.java).publications {
        // Create a Maven publication named "release".
        create("release", MavenPublication::class.java) {
            // Explicitly specify the AAR file to publish
            artifact("$buildDir/outputs/aar/${project.name}-release.aar")

            groupId = "com.github.chinchin"
            artifactId = "palletview"
            version = "1.0"
        }
    }

    // Repositories for publishing
    extensions.getByType(PublishingExtension::class.java).repositories {
        maven {
            name = "jitpack"
            url = uri("https://jitpack.io")
        }
    }
}
