package com.zavedahmad.AppLauncher.ui.mainPage

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation3.runtime.NavKey
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


data class AppInfo(val name: String, val packageName: String, val icon: Drawable)

@OptIn(
    ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun MainPage(backStack: SnapshotStateList<NavKey>, viewModel: MainPageViewModel) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val packageManager = context.packageManager
    val appQueryPermissionState =
        rememberPermissionState(android.Manifest.permission.QUERY_ALL_PACKAGES)

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (appQueryPermissionState.status.isGranted) {

                val pm = context.packageManager
                val mainIntent = Intent(Intent.ACTION_MAIN, null)
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

                val apps = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    pm.queryIntentActivities(
                        mainIntent,
                        PackageManager.ResolveInfoFlags.of(0L)
                    )
                } else {
                    pm.queryIntentActivities(mainIntent, 0)
                }
                var unsortedList = mutableListOf<AppInfo>()
                apps.forEach {
                    val resources =  pm.getResourcesForApplication(it.activityInfo.applicationInfo)
                    val appName = if (it.activityInfo.labelRes != 0) {
                        // getting proper label from resources
                        resources.getString(it.activityInfo.labelRes)
                    } else {
                        // getting it out of app info - equivalent to context.packageManager.getApplicationInfo
                        it.activityInfo.applicationInfo.loadLabel(pm).toString()
                    }
                    unsortedList.add(
                        AppInfo(
                            name = appName,
                            packageName = it.activityInfo.packageName,
                            icon = it.activityInfo.loadIcon(pm)
                        )
                    )

                }
                val appsInfoList= unsortedList.sortedBy { it.name.lowercase() }

                LazyVerticalGrid(columns = GridCells.Fixed(5)) {
                    items(appsInfoList.size) { index ->
                        val item = appsInfoList[index]

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectTapGestures(onTap = {val packageName = item.packageName // Replace with target app's package name
                                        val intent = context.packageManager.getLaunchIntentForPackage(packageName)

                                            context.startActivity(intent)}, onLongPress = {
                                        val uri =
                                            "app-manager://details?id=${item.packageName}&user=0".toUri() // Replace with your URL
                                        val intent = Intent(Intent.ACTION_VIEW, uri)
                                        context.startActivity(intent)
                                    })
                                }
                               ,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                modifier = Modifier.size(80.dp),
                                model = item.icon,
                                contentDescription = ""
                            )
                            Text(item.name, fontSize = 15.sp, lineHeight = 15.sp, fontWeight = FontWeight.Thin,maxLines = 2, overflow = TextOverflow.Ellipsis)

                        }

                    }
                }

            } else {
                Button(onClick = { appQueryPermissionState.launchPermissionRequest() }) {
                    Text("Request permission")
                }
            }
        }
    }

}
