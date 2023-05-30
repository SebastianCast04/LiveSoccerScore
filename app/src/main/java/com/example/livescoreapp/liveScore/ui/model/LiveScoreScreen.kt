package com.example.livescoreapp.liveScore.ui.model

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.livescoreapp.liveScore.data.remote.model.ModelMatch
import com.example.livescoreapp.liveScore.ui.viewmodel.CurrentMatchesViewModel
import com.example.livescoreapp.liveScore.ui.viewmodel.UpcomingMatchesViewModel
import com.example.livescoreapp.liveScore.ui.viewmodel.state.MatchesState
import java.text.SimpleDateFormat
import java.util.Locale

//@Preview(showBackground = true)

@Composable
fun LiveMatchItem(match: ModelMatch) {


    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .width(300.dp)
            .height(150.dp)
    ) {

        Column(modifier = Modifier.padding(10.dp)) {

            Text(
                text = match.leagueName,
                modifier = Modifier.align(CenterHorizontally),
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                val homeScore = match.team_home_90min_goals
                val awayScore = match.team_away_90min_goals

                Text(
                    text = match.homeName,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
                Text(text = "$homeScore:$awayScore", fontWeight = FontWeight.SemiBold)
                Text(
                    text = match.awayName,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }

    }

}

@Composable
fun UpcomingMatchesItem(match: ModelMatch) {


    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(bottom = 10.dp)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            val month = getMatchDayAndMonth(match.date)
            val time = getMatchTime(match.date)

            Text(
                text = match.homeName,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )

            Text(text = "$time\n$month", color = Color.Green, textAlign = TextAlign.Center)

            Text(
                text = match.awayName,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }

}


fun getMatchDayAndMonth(date: String): String? {

    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val CustomFormat = SimpleDateFormat("d MMM", Locale.ENGLISH)
    return date.let { it -> parser.parse(it)?.let { CustomFormat.format(it) } }
}


fun getMatchTime(date: String): String? {

    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val CustomFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    return date.let { it -> parser.parse(it)?.let { CustomFormat.format(it) } }
}


@Composable
fun TopAppBar() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {

            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")

        }

        Text(
            text = "LiveScores",
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )

        IconButton(onClick = {}) {

            Icon(imageVector = Icons.Outlined.LightMode, contentDescription = "")

        }
    }

}

@Composable
fun data(
    currentMatchesViewModel: CurrentMatchesViewModel = viewModel(),
    upcomingMatchesViewModel: UpcomingMatchesViewModel = viewModel()
) {

    Column {

        when (val state = currentMatchesViewModel.currentMatchesState.collectAsState().value) {

            is MatchesState.Empty -> Text(text = "There is not data available")
            is MatchesState.Loading -> Text(text = "Loading")
            is MatchesState.Success -> RunningMatches(liveMatches = state.data)
            is MatchesState.Error -> Text(text = state.message)
        }

        when (val state = upcomingMatchesViewModel.upComingMatchesState.collectAsState().value) {

            is MatchesState.Empty -> Text(text = "There is not data available")
            is MatchesState.Loading -> Text(text = "Loading")
            is MatchesState.Success -> UpcomingMatches(upcomingMatches = state.data)
            is MatchesState.Error -> Text(text = state.message)
        }

    }
}


@Composable
fun RunningMatches(liveMatches: List<ModelMatch>) {

    Column(modifier = Modifier.padding(15.dp)) {

        Text(text = "Live Matches", style = MaterialTheme.typography.bodyMedium)

        if (liveMatches.isEmpty()) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Icon(imageVector = Icons.Default.Info, contentDescription = "info")
                Text(text = "No live matches currently")
            }

        } else {

            LazyRow(
                modifier = Modifier.padding(top = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(liveMatches.size) {

                    LiveMatchItem(match = liveMatches[it])

                }
            }

        }

    }
}

@Composable
fun UpcomingMatches(upcomingMatches: List<ModelMatch>) {

    Column(modifier = Modifier.padding(15.dp)) {

        Text(text = "Scheduled Matches", style = MaterialTheme.typography.bodyMedium)

        if (upcomingMatches.isEmpty()) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Icon(imageVector = Icons.Default.Info, contentDescription = "info")
                Text(text = "No upcoming matches currently")
            }

        } else {

            LazyColumn(
                modifier = Modifier.padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                items(upcomingMatches.size) {

                    UpcomingMatchesItem(match = upcomingMatches[it])

                }
            }

        }

    }
}