package com.aptivist.dogchallenge.ui.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aptivist.dogchallenge.R
import com.aptivist.dogchallenge.ui.theme.DogChallengeTheme
import com.aptivist.dogchallenge.ui.viewmodels.MainUIState
import com.aptivist.dogchallenge.ui.viewmodels.MainViewModel
import com.aptivist.dogchallenge.ui.viewmodels.UIActions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val vm : MainViewModel = viewModel()

                    val currentDog = remember {vm.currentDog}
                    val uiState = remember {vm.uiState}

                    val context = LocalContext.current

                    LaunchedEffect(key1 = true) {
                        vm.uiActions.collect {
                            when (it) {
                                is UIActions.UIShowError -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(32.dp)) {




                        when(uiState.value) {
                            MainUIState.Idle -> {

                                if(currentDog.value.isNotEmpty()) {
                                    Box( modifier = Modifier
                                        .size(300.dp)
                                        .clip(RoundedCornerShape(5))) {
                                        GlideImage(
                                            imageModel = { currentDog.value },
                                            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                                else
                                {
                                    Spacer(modifier = Modifier.size(300.dp))
                                }

                                Button(onClick = { vm.getDogImage() }) {
                                    Text(text = "Get random dog")
                                }
                            }
                            MainUIState.Loading -> {
                                Box(modifier = Modifier.size(300.dp)){
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }
                                Text(text = stringResource(id = R.string.loading))
                            }
                        }
                    }

                }
            }
        }
    }
}