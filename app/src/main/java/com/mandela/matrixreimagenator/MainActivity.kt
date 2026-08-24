package com.mandela.matrixreimagenator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mandela.matrixreimagenator.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(Modifier = Modifier.fillMaxSize()) {
                    MandelaApp(onAbout = { showAbout() })
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_recreate -> { Toast.makeText(this, "Recreate App", Toast.LENGTH_SHORT).show(); true }
        R.id.action_swarm -> { Toast.makeText(this, "Swarm Builder", Toast.LENGTH_SHORT).show(); true }
        R.id.action_vibe -> { Toast.makeText(this, "Personal Vibe Adjust", Toast.LENGTH_SHORT).show(); true }
        R.id.action_tools -> { Toast.makeText(this, "Modular Tools", Toast.LENGTH_SHORT).show(); true }
        R.id.action_about -> { showAbout(); true }
        else -> super.onOptionsItemSelected(item)
    }

    private fun showAbout() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Mandela vs Matrix Re-imagenator")
            .setMessage("Version 1.0.0\n\nGiant creation assistant + swarm builder\nRecreate apps with personal vibe adjustment\n\nBuilt by REDRUM Studios")
            .setPositiveButton("OK", null).show()
    }
}

enum class Tab(val title: String, val icon: ImageVector) {
    Core("Core", Icons.Default.GridView),
    Mandela("Mandela", Icons.Default.BlurOn),
    Swarm("Swarm", Icons.Default.Build),
    Vibe("Vibe", Icons.Default.Tune),
    Tools("Tools", Icons.Default.Science)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandelaApp(onAbout: () -> Unit) {
    val tabs = Tab.entries
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mandela Re-imagenator • ${tabs[pagerState.currentPage].title}") }
            )
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            ScrollableTabRow(selectedTabIndex = pagerState.currentPage, edgePadding = 8.dp) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                        text = { Text(tab.title) },
                        icon = { Icon(tab.icon, tab.title) }
                    )
                }
            }
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                when (tabs[page]) {
                    Tab.Core -> CoreScreen()
                    Tab.Mandela -> MandelaCoreScreen()
                    Tab.Swarm -> SimpleCardScreen("Swarm Builder", "Deploy agent swarms to rebuild apps with your vibe.")
                    Tab.Vibe -> SimpleCardScreen("Personal Vibe", "Adjust tone, style and personality of generated code.")
                    Tab.Tools -> SimpleCardScreen("Modular Tools", "APK auditor, image tools, training centre.")
                }
            }
        }
    }
}

@Composable
fun CoreScreen() {
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Re-imagenator Core", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Recreate any app with personal vibe adjustment. Built by REDRUM Studios.")
        Card(Modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Quick Actions", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Recreate App") }
                OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Import Project") }
            }
        }
    }
}

data class RealityNode(val id: String, val name: String, val coherence: Float, val glitching: Boolean = false)

@Composable
fun MandelaCoreScreen() {
    var quantumGlitch by remember { mutableStateOf(false) }
    var nodes by remember {
        mutableStateOf(listOf(
            RealityNode("n1", "Primary Timeline", 0.94f),
            RealityNode("n2", "Memory Anchor", 0.81f),
            RealityNode("n3", "Identity Lattice", 0.76f),
            RealityNode("n4", "Echo Chamber", 0.62f),
            RealityNode("n5", "Drift Buffer", 0.88f)
        ))
    }
    val avg = nodes.map { it.coherence }.average().toFloat()

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Surface(tonalElevation = 2.dp, modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.BlurOn, null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(8.dp))
                    Text("MandelaCore", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
                Text("Reality matrix • coherence ${(avg * 100).toInt()}%")
            }
        }
        Card(modifier = Modifier.fillMaxWidth().padding(12.dp), shape = RoundedCornerShape(12.dp)) {
            Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Quantum Glitch", fontWeight = FontWeight.SemiBold)
                    Text(if (quantumGlitch) "ACTIVE" else "Stable continuum")
                }
                Switch(checked = quantumGlitch, onCheckedChange = { on ->
                    quantumGlitch = on
                    nodes = nodes.map { n -> n.copy(glitching = on, coherence = if (on) (n.coherence * 0.85f).coerceAtLeast(0.2f) else (n.coherence / 0.85f).coerceAtMost(1f)) }
                })
            }
        }
        Text("Reality Nodes", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(nodes, key = { it.id }) { node ->
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(node.name, fontWeight = FontWeight.SemiBold)
                            Text("${(node.coherence * 100).toInt()}%", color = when {
                                node.coherence >= 0.85f -> Color(0xFF69F0AE)
                                node.coherence >= 0.6f -> Color(0xFFFFAB00)
                                else -> Color(0xFFFF5252)
                            }, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(6.dp))
                        LinearProgressIndicator(progress = { node.coherence }, modifier = Modifier.fillMaxWidth().height(6.dp))
                        if (node.glitching) Text("GLITCHING", color = Color(0xFFFF00E5), style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleCardScreen(title: String, body: String) {
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(body)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Coming online…")
                Text("Built by REDRUM Studios", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
