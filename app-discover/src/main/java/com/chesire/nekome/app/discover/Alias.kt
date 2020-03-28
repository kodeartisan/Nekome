package com.chesire.nekome.app.discover

import com.chesire.nekome.core.flags.AsyncState
import com.chesire.nekome.core.models.SeriesModel

/**
 * typealias for data returning from the main trending end point.
 */
typealias TrendingData = AsyncState<List<SeriesModel>, DiscoverError>
