package com.swordhealth.thecat.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.swordhealth.thecat.data.api.CatsApi
import com.swordhealth.thecat.data.entities.CatEntity
import java.io.IOException

class CatsPagingSource(
    private val catsApi: CatsApi
) : PagingSource<Int, CatEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatEntity> {
        val currentPage = params.key ?: 1
        return try {
            val response = catsApi.getCats(page = currentPage)

            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: retrofit2.HttpException) {
            return LoadResult.Error(exception)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}