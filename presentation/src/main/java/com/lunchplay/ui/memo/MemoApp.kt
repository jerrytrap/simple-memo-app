package com.lunchplay.ui.memo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lunchplay.ui.memo.model.MemoUiModel

@Composable
fun MemoApp() {
    val navController = rememberNavController()

    MemoNavHost(navController = navController)
}

@Composable
fun MemoNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "memoList") {
        composable("memoList",) {
            MemoListScreen(
                onItemClick = { item ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("memoInfo", item)
                    navController.navigate("memoDetail")
                }
            )
        }
        composable("memoDetail") {
            val memoInfo = navController.previousBackStackEntry?.savedStateHandle?.get<MemoUiModel>("memoInfo")

            if (memoInfo != null) {
                MemoDetailScreen(
                    memo = memoInfo,
                    onBackButtonClick = { navController.popBackStack() }
                )
            }
        }
    }
}

