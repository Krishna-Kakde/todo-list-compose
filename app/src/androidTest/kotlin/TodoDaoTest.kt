import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todo.database.local.TodoDao
import com.example.todo.database.local.TodoDatabase
import com.example.todo.database.local.TodoEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class TodoDaoTest {
    private lateinit var todoDao: TodoDao
    private lateinit var todoDatabase: TodoDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        todoDatabase = Room.inMemoryDatabaseBuilder(context, TodoDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        todoDao = todoDatabase.todoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        todoDatabase.close()
    }

    private var todo1 = TodoEntity(1,"complete an APP","complete the recent android project",false)
    private var todo2 = TodoEntity(2,"call sam","Wish him birthday wish",false)

    private suspend fun addOneTodoToDb() {
        todoDao.insertTodo(todo1)
    }
    private suspend fun addTwoTodoToDb() {
        todoDao.insertTodo(todo1)
        todoDao.insertTodo(todo2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertTodoIntoDb() = runBlocking {
        addOneTodoToDb()

        val allTodos = todoDao.getAllTodos().first()
        assertEquals(allTodos[0],todo1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllTodos_returnsAllTodosFromDB() = runBlocking {
        addTwoTodoToDb()

        val allTodos = todoDao.getAllTodos().first()
        assertEquals(allTodos[0],todo2)
        assertEquals(allTodos[1],todo1)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateTodos_updatesTodosInDB () = runBlocking {
        addTwoTodoToDb()
        todoDao.updateTodo(TodoEntity(1,"complete an APP","complete the recent android project",true))
        todoDao.updateTodo(todo2.copy(isCompleted = true))

        val allTodos = todoDao.getAllTodos().first()
        assertEquals(allTodos[0],todo2.copy(isCompleted = true))
        assertEquals(allTodos[1],todo1.copy(isCompleted = true))
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteTodo_deleteTodoFromDb() = runBlocking {
        addTwoTodoToDb()
        todoDao.deleteTodo(todo1)

        val allTodos = todoDao.getAllTodos().first()
        assertEquals(1,allTodos.size)
        assertFalse(allTodos.contains(todo1))
        assertTrue(allTodos.contains(todo2))
    }
}
