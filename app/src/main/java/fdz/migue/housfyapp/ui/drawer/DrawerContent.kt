package fdz.migue.housfyapp.ui.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fdz.migue.housfyapp.R
import fdz.migue.housfyapp.features.profile.ProfileViewModel
import fdz.migue.housfyapp.ui.components.MenuItem

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    onNavigate: (String) -> Unit = {}
){
    val profile = viewModel.profile.collectAsState(initial = null)
    ProfileContent(
        viewModel,
        onEditProfile = {onNavigate("profileedit") }
    )
    HorizontalDivider()
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        MenuItem(
            icon = Icons.Default.Home,
            text = stringResource(R.string.drawer_home),
            onClick = {onNavigate("home")}
        )
        MenuItem(
            icon = Icons.Default.AddCircle,
            text = stringResource(R.string.drawer_tasks),
            onClick = {onNavigate("tasks")}
        )
        MenuItem(
            icon = Icons.Default.DateRange,
            text = stringResource(R.string.drawer_activities),
            onClick = {onNavigate("activities")}
        )
        MenuItem(
            icon = Icons.Default.ShoppingCart,
            text = stringResource(R.string.drawer_shopping),
            onClick = {onNavigate("shopping")}
        )
        MenuItem(
            icon = Icons.Default.MailOutline,
            text = stringResource(R.string.drawer_chat),
            onClick = {onNavigate("chat")}
        )

        Spacer(modifier = Modifier.weight(1f))
        HorizontalDivider()

        MenuItem(
            icon = Icons.Default.Settings,
            text = stringResource(R.string.drawer_config),
            onClick = {onNavigate("conf")}
        )
    }
}
