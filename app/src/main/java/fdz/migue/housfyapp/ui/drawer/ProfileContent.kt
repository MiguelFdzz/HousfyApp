package fdz.migue.housfyapp.ui.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    name: String,
    photoUrl: String? = null,
    onEditProfile: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .size(56.dp)
                .align(Alignment.Bottom)
        ) {
            if (photoUrl.isNullOrEmpty()){
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .fillMaxSize()
                )
            } else {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
            Box(
                modifier = modifier
                    .size(19.dp)
                    .offset(x = 30.dp, y = 30.dp)
                    .padding(2.dp)
                    .background(
                        color = Color.Green,
                        shape = CircleShape
                    )
            )
        }
        Text(text = name, fontSize = 17.sp)

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Editar perfil",
            modifier = Modifier
                .size(30.dp)
                .padding(end = 8.dp)
                .clickable {
                    onEditProfile()
                }
        )
    }
}