import { configureStore } from '@reduxjs/toolkit'
import userReducer from './slices/userSlice'
import tokenReducer from './slices/tokenSlice'

const reducer = {
    user: userReducer,
    token: tokenReducer
}

const store = configureStore({
    reducer
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispath = typeof store.dispatch

export default store
