package fdz.migue.housfyapp.features.profile

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import fdz.migue.housfyapp.dao.profile.Profile
import fdz.migue.housfyapp.ui.components.RoundedBackground

@Composable
fun PEditScreen(
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.profile.collectAsState(initial = null)

    var name by remember { mutableStateOf("Usuario") }
    var selectedStatus by remember { mutableStateOf(UserStatus.ACTIVO) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(profile) {
        profile?.let {
            name = it.name
            selectedStatus = UserStatus.valueOf(it.status)

            profileImageUri = it.profileImageUri?.toUri()
        }
    }

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION

            context.contentResolver.takePersistableUriPermission(it, takeFlags)

            profileImageUri = it
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            RoundedBackground(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "¡Bienvenid@ al editor del perfil",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            RoundedBackground {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.padding(bottom = 16.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .size(128.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFBA68C8),
                                            Color(0xFF42A5F5)
                                        )
                                    )
                                )
                                .border(4.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (profileImageUri != null) {
                                AsyncImage(
                                    model = profileImageUri,
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Default Profile",
                                    modifier = Modifier.size(64.dp),
                                    tint = Color.White
                                )
                            }
                        }

                        IconButton(
                            onClick = { imagePickerLauncher.launch(arrayOf("image/*"))},
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2196F3))
                                .border(3.dp, Color.White, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Change Photo",
                                tint = Color.White
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                    ) {
                        Text(
                            text = "Nombre",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Ingresa tu nombre") },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.LightGray
                            )
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                    ) {
                        Text(
                            text = "Estado",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        UserStatus.entries.forEach { status ->
                            StatusOption(
                                status = status,
                                isSelected = selectedStatus == status,
                                onClick = { selectedStatus = status },
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            val newProfile = Profile(
                                id = profile?.id ?: 0,
                                name = name.ifBlank { "Usuario" },
                                status = selectedStatus.name,
                                profileImageUri = profileImageUri?.toString()
                            )

                            viewModel.saveProfile(newProfile)

                            Toast.makeText(
                                context,
                                "Perfil guardado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue
                        )
                    ) {
                        Text(
                            text = "Guardar Cambios",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Vista Previa",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.LightGray.copy(alpha = 0.1f),
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                        .background(
                                            Brush.linearGradient(
                                                colors = listOf(
                                                    Color(0xFFBA68C8),
                                                    Color(0xFF42A5F5)
                                                )
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (profileImageUri != null) {
                                        AsyncImage(
                                            model = profileImageUri,
                                            contentDescription = "Preview",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Default",
                                            modifier = Modifier.size(32.dp),
                                            tint = Color.White
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(selectedStatus.color)
                                )
                            }

                            Column(
                                modifier = Modifier.padding(start = 16.dp)
                            ) {
                                Text(
                                    text = name.ifEmpty { "Usuario" },
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = selectedStatus.label,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}