import { configureStore } from '@reduxjs/toolkit'
import colorReducer from "./slices/colorSlice";

export default configureStore({
    reducer: {
        color: colorReducer
    }
})

