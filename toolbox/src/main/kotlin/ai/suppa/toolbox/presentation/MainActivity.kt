package ai.suppa.toolbox.presentation

import ai.suppa.launchSuppa
import ai.suppa.toolbox.R
import ai.suppa.toolbox.presentation.theme.SuppaToolboxTheme
import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch { viewModel.loadChatbots() }

        setContent {
            SuppaToolboxTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()

                when (state) {
                    MainViewModel.State.Loading -> {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = stringResource(id = R.string.loading),
                                )
                            }
                        }
                    }

                    is MainViewModel.State.Error -> {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = (state as MainViewModel.State.Error).message,
                                )
                            }
                        }
                    }

                    is MainViewModel.State.Content -> {
                        val contentState = state as MainViewModel.State.Content
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            topBar = {
                                TopAppBar(
                                    title = { Text(text = stringResource(id = R.string.toolbar_title)) },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                    ),
                                )
                            },
                        ) { innerPadding ->
                            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                                for (index in contentState.chatbots.indices) {
                                    item {
                                        ListItem(
                                            modifier = Modifier.clickable {
                                                launchSuppa(contentState.chatbots[index].id)
                                            },
                                            headlineContent = {
                                                Text(text = contentState.chatbots[index].name)
                                            },
                                            supportingContent = {
                                                contentState.chatbots[index].companyName?.let {
                                                    Text(text = it)
                                                }
                                            },
                                            trailingContent = {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                                                    contentDescription = null,
                                                )
                                            },
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
