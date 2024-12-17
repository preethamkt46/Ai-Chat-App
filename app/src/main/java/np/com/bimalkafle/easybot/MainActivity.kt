package np.com.bimalkafle.easybot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import np.com.bimalkafle.easybot.ui.theme.IssacTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            IssacTheme(darkTheme = isDarkTheme) {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent(
                            onDismiss = {
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            isDarkTheme = isDarkTheme,
                            onThemeChange = { isDarkTheme = it }
                        )
                    },
                    content = {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            ChatPage(
                                modifier = Modifier.padding(innerPadding),
                                viewModel = chatViewModel,
                                onMenuClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DrawerContent(
    onDismiss: () -> Unit,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "Issac", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
            Text("An Ai Assistant", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(40.dp))

            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))

            Spacer(modifier = Modifier.height(20.dp))
            Text("Developers:", color = MaterialTheme.colorScheme.onSurface)
            Text("Preetham K T", color = MaterialTheme.colorScheme.onSurface)
            Text("Sathwik M", color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(18.dp))

            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))

            // Toggle button for theme change
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Theme", color = MaterialTheme.colorScheme.onSurface)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = if (isDarkTheme) "Light  " else "Dark  ", color = MaterialTheme.colorScheme.onSurface)
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = onThemeChange
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 16.dp)
        ) {
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Version 1.0.1", color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onDismiss) {
                Text("Close", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
