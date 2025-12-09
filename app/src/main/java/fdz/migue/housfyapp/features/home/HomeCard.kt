package fdz.migue.housfyapp.features.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fdz.migue.housfyapp.ui.components.RoundedBackground

@Composable
fun HomeCard(
    text: String,
    modifier: Modifier = Modifier
) {
    RoundedBackground(modifier = modifier.padding(bottom = 8.dp).fillMaxWidth()) {
        Text(text = text, fontSize = 25.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }
}