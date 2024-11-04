import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.challenge.ui.theme.CrudScreen
import android.os.Bundle


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {

                    CrudScreen() // Aqui é onde a sua tela CRUD deve ser chamada

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCrudScreen() {
    CrudScreen()
}
