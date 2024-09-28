
# Aula 01 - Criação do projeto e dependências necessárias

### Criação do projeto

Empyt Compose Activity
Nome do projeto: TodoList
SDK mínimo: 26
Build configuration language: Kotlin DSL

- [ ]  Limpar a acitivty e deixar apenas o TodoListTheme
- [ ]  Atualizar a versão do Kotlin para 2.0.20 e das outras dependências e configurar o plugin do KSP (versão do Kotlin e KSP devem bater) e Compose Compiler

   ```toml
   [versions]
   ...
   kotlin = "2.0.20"
   ksp = "2.0.20-1.0.24"
   
   [libraries]
   ...
   
   [plugins]
   ...
   ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
   compose-compiler = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
   ```

   ```groovy
   plugins {
       alias(libs.plugins.android.application)
       alias(libs.plugins.jetbrains.kotlin.android)
       alias(libs.plugins.ksp)
       alias(libs.plugins.compose.compiler)
       alias(libs.plugins.hilt.android)
   }
   ```

- [ ]  Adicionar as dependências do projeto

   ```toml
   [versions]
   ...
   room = "2.6.1"
   room = "2.6.1"
   navigation = "2.8.0"
   lifecycle = "2.8.5"
   hilt = "2.51.1"
   
   [libraries]
   ...
   androidx-room-runtime = { module = "androidx.room:room-runtime", version.ref = "room" }
   androidx-room-compiler = { module = "androidx.room:room-compiler", version.ref = "room" }
   androidx-room-ktx = { module = "androidx.room:room-ktx", version.ref = "room" }
   androidx-navigation-compose = { module = "androidx.navigation:navigation-compose", version.ref = "navigation" }
   androidx-lifecycle-viewmodel-compose = { module = "androidx.lifecycle:lifecycle-viewmodel-compose", version.ref = "lifecycle" }
   androidx-lifecycle-runtime-compose = { module = "androidx.lifecycle:lifecycle-runtime-compose", version.ref = "lifecycle" }
   hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hilt" }
   hilt-android-compiler = { module = "com.google.dagger:hilt-android-compiler", version.ref = "hilt" }
   
   [plugins]
   ...
   hilt-android = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
   
   ```

   ```groovy
   plugins {
       ...
       alias(libs.plugins.hilt.android)
   }
   
   dependencies {
   
       ...
   
       // Room
       implementation(libs.androidx.room.runtime)
       ksp(libs.androidx.room.compiler)
       implementation(libs.androidx.room.ktx)
   
       // Navigation
       implementation(libs.androidx.navigation.compose)
   
       // Lifecycle
       implementation(libs.androidx.lifecycle.viewmodel.compose)
       implementation(libs.androidx.lifecycle.runtime.compose)
   
       // Dagger Hilt
       implementation(libs.hilt.android)
       ksp(libs.hilt.android.compiler)
   
       ...
   }
   ```

- [ ]  Executar o aplicativo