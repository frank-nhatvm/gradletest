plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
version = "1.4.5"

android {
    namespace = "com.frank.jchart"
    compileSdk = 33

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
        println("namingAARFile task:  Version $version")

        val aarFilePath = "${project.projectDir}/build/outputs/aar/${project.name}-release.aar"
        println("aarFilePath : $aarFilePath")
        val aarFile = File(aarFilePath)
        if (aarFile.exists()) {
            println("AAR file exists")
            val newAARFileName = "${project.name}-$version.aar"
            val renamedFile = File(aarFile.parent, newAARFileName)
            aarFile.renameTo(renamedFile)
        } else {
            println("AAR file does not exists")
        }
    }
}


afterEvaluate {
    tasks.named("assembleRelease"){
        dependsOn("test")
        finalizedBy("namingAARFile")
    }
}


//tasks.register("namiSDKRelease"){
//    dependsOn("assembleRelease")
//    finalizedBy("namingAARFile")
//}

