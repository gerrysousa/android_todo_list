
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


# Aula 04 - Configurando a navegação

- [ ]  Adicionar o plugin e a dependência do kotlinx serialization com Json

   ```toml
   [versions]
   ...
   kotlinxSerialization = "1.7.1"
   
   [libraries]
   ...
   kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "kotlinxSerialization" }
   ```

   ```groovy
   plugins {
       ...
       kotlin("plugin.serialization") version libs.versions.kotlin
   }
   
   dependencies {
   
       ...
   
       // Room
       ...
   
       // Navigation
       ...
       
       // Lifecycle
       ...
   
       // Dagger Hilt
       ...
   
       // Kotlinx Serialization
       implementation(libs.kotlinx.serialization.json)
   
       ...
   }
   ```

- [ ]  Criar o pacote navigation dentro do pacote principal e criar o NavHost

   ```kotlin
   @Serializable
   object ListRoute
   
   @Serializable
   data class AddEditRoute(val id: Long? = null)
   
   @Composable
   fun TodoNavHost() {
       val navController = rememberNavController()
       NavHost(navController = navController, startDestination = ListRoute) {
           composable<ListRoute> {
               ListScreen()
           }
   
           composable<AddEditRoute> { backStackEntry ->
               val addEditRoute = backStackEntry.toRoute<AddEditRoute>()
               AddEditScreen()
           }
       }
   }
   ```

- [ ]  Adicionar as ações na tela de Listagem para redirecionar para a tela de adicionar/editar

   ```kotlin
   @Composable
   fun ListScreen(
       navigateToAddEditScreen: (id: Long?) -> Unit,
   ) {
       ListContent(
           todos = emptyList(),
           onAddItemClick = {
               navigateToAddEditScreen(null)
           }
       )
   }
   
   @Composable
   fun ListContent(
       todos: List<Todo>,
       onAddItemClick: () -> Unit,
   ) {
       Scaffold(
           floatingActionButton = {
               FloatingActionButton(
                   onClick = {
                       onAddItemClick()
                   }
               ) {
                   Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
               }
           }
       ) {
           ...
       }
   }
   
   @Preview
   @Composable
   private fun ListContentPreview() {
       TodoListBaseTheme {
           ListContent(
               todos = listOf(
                   todo1,
                   todo2,
                   todo3,
               ),
               onAddItemClick = {},
           )
       }
   }
   ```

- [ ]  Ajustar NavHost e navegar para a tela de adicionar/editar

   ```kotlin
   ...
   
   @Composable
   fun TodoNavHost() {
      ...
       NavHost(navController = navController, startDestination = ListRoute) {
           composable<ListRoute> {
               **ListScreen(
                   navigateToAddEditScreen = { id ->
                       navController.navigate(AddEditRoute(id))
                   }
               )**
           }
   
           ...
       }
   }
   ```

- [ ]  Chamar o NavHost na Activity

   ```kotlin
   class MainActivity : ComponentActivity() {
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           enableEdgeToEdge()
           setContent {
               TodoListBaseTheme {
                   **TodoNavHost()**
               }
           }
       }
   }
   ```

- [ ]  Executar o aplicativo e verficar que a tela de adicionar/editar ficou com o conteúdo atrás da status bar por causa da feature edge to edge do Android. Como nosso aplicativo nao precisa fazer uso edge to edge, podemos ajustar para utilizar a penas a área segura

   ```kotlin
   class MainActivity : ComponentActivity() {
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           enableEdgeToEdge()
           setContent {
               TodoListBaseTheme {
                   **Box(modifier = Modifier.safeDrawingPadding()) {
                       TodoNavHost()
                   }**
               }
           }
       }
   }
   ```

Aula 05 - Implementando a camada de dados
Criar o pacote data dentro do pacote principal
Criar a classe de dados que vai representar a entidade Todo no banco de dados
@Entity(tableName = "todos")
data class TodoEntity(
@PrimaryKey(autoGenerate = true) val id: Long = 0,
val title: String,
val description: String?,
val isCompleted: Boolean,
)
data/TodoEntity.kt
​
Criar a interface Dao que vai permitir fazer acesso aos dados do banco de dados
@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TodoEntity)

    @Delete
    suspend fun delete(entity: TodoEntity)

    @Query("SELECT * FROM todos")
    fun getAll(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getBy(id: Long): TodoEntity?
}
data/TodoDao.kt
​
Criar o banco de dados Room e o objeto provider que vai fornecer um instância única do banco de dados
@Database(
entities = [TodoEntity::class],
version = 1,
)
abstract class TodoDatabase : RoomDatabase() {

    abstract val dao: TodoDao
}

object TodoDatabaseProvider {

    @Volatile
    private var INSTANCE: TodoDatabase? = null

    fun provide(context: Context): TodoDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "todo-app"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
​
Criar a interface do Repositório que vai ficar responsável por fornecer a abstração de como acessar os dados da fonte de dados
interface TodoRepository {

    suspend fun insert(title: String, description: String?)
    
    suspend fun updateCompleted(id: Long, isCompleted: Boolean)

    suspend fun delete(id: Long)

    fun getAll(): Flow<List<Todo>>

    suspend fun getBy(id: Long): Todo?
}
​
Implementar a interface com os detalhes de implementação de acesso aos dados
class TodoRepositoryImpl(
private val dao: TodoDao,
) : TodoRepository {

    override suspend fun insert(title: String, description: String?) {
        val entity = TodoEntity(
            title = title,
            description = description,
            isCompleted = false,
        )

        dao.insert(entity)
    }
    
    override suspend fun updateCompleted(id: Long, isCompleted: Boolean) {
        val existentEntity = dao.getBy(id) ?: return
        val updatedEntity = existentEntity.copy(isCompleted = isCompleted)
        dao.insert(updatedEntity)
    }

    override suspend fun delete(id: Long) {
        val existentEntity = dao.getBy(id) ?: return
        dao.delete(existentEntity)
    }

    override fun getAll(): Flow<List<Todo>> {
        return dao.getAll().map {
            it.map { entity ->
                Todo(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    isCompleted = entity.isCompleted
                )
            }
        }
    }

    override suspend fun getBy(id: Long): Todo? {
        return dao.getBy(id)?.let { entity ->
            Todo(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                isCompleted = entity.isCompleted
            )
        }
    }
}




