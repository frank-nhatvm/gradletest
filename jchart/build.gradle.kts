import com.android.build.gradle.tasks.PackageApplication

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
version = "1.4.5"

android {
    namespace = "com.frank.jchart"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = true
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

tasks.register("namingAARFile") {
    doLast {
        val version = project.version.toString()
        val aarFilePath = "${project.projectDir}/build/outputs/aar/${project.name}-release.aar"
        val aarFile = File(aarFilePath)
        if (aarFile.exists()) {
            val newAARFileName = "${project.name}-$version.aar"
            val renamedFile = File(aarFile.parent, newAARFileName)
            aarFile.renameTo(renamedFile)
            println("Task Result: ${renamedFile.path}")
        }else{
            println("Task Result:build/outputs/aar/fail.aar")
        }
    }
}

tasks.register("releaseSDK"){
    dependsOn("test","assembleRelease")
    finalizedBy("namingAARFile")
}


