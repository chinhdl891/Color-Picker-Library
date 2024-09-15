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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.chinhdl891"  // Replace with your GitHub username
            artifactId = "Color-Picker-Library"        // The repository name
            version = "1.0.0"                  // Update version as needed

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Color Picker Library")
                description.set("A library for color picking")
                url.set("https://github.com/chinhdl891/Color-Picker-Library")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("chinhdl891")
                        name.set("Chin Chin")
                        email.set("vuquocchinh891@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/chinhdl891/Color-Picker-Library.git")
                    developerConnection.set("scm:git:ssh://github.com/chinhdl891/Color-Picker-Library.git")
                    url.set("https://github.com/chinhdl891/Color-Picker-Library")
                }
            }
        }
    }
}
