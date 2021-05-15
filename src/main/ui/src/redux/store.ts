import { configureStore } from '@reduxjs/toolkit'
import colorReducer from "./slices/colorSlice";

const reducer = {
    color: colorReducer
}

const store = configureStore({
    reducer
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispath = typeof store.dispatch

export default store
