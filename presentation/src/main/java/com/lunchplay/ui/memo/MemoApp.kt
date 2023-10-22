package com.lunchplay.ui.memo

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
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
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = "memoList") {
        composable("memoList",) {
            MemoListScreen(
                onItemClick = { item ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("memoInfo", item)
                    navController.navigate("memoDetail")
                },
                onCreateButtonClick = {
                    navController.navigate("memoCreate")
                }
            )
        }
        composable("memoCreate") {
            MemoCreateScreen(
                onSaveButtonClick = { navController.popBackStack() }
            )
        }
        composable("memoDetail") {
            val memoInfo = navController.previousBackStackEntry?.savedStateHandle?.get<MemoUiModel>("memoInfo")
            navController.currentBackStackEntry?.savedStateHandle?.set("memoInfo", memoInfo)

            if (memoInfo != null) {
                MemoDetailScreen(
                    memo = memoInfo,
                    onBackButtonClick = { navController.popBackStack() },
                    onEditMenuClick = { navController.navigate("memoEdit") },
                    onDeleteMenuClick = { navController.popBackStack() }
                )
            }
        }
        composable("memoEdit") {
            val memoInfo = navController.previousBackStackEntry?.savedStateHandle?.get<MemoUiModel>("memoInfo")

            if (memoInfo != null) {
                MemoEditScreen(
                    memo = memoInfo,
                    onSaveButtonClick = {
                        navController.navigate("memoList") {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}

fun showToast(context: Context, @StringRes id: Int) {
    Toast.makeText(context, context.getString(id), Toast.LENGTH_SHORT).show()
}

