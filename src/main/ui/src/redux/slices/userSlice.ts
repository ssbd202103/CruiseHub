import {createSlice, PayloadAction} from '@reduxjs/toolkit'

export interface IUserSliceState {
    firstName: string,
    secondName: string,
    email: string,
    login: string,
    etag: string,
    version: number,
    languageType: string,
    accessLevels: Array<{
        '@type': "client" | "businessWorker" | "moderator" | "administrator",
        accessLevelType: "CLIENT" | "BUSINESS_WORKER" | "MODERATOR" | "ADMINISTRATOR",
        address?: {
            houseNumber: number,
            street: string,
            postalCode: string,
            city: string,
            country: string
        },
        phoneNumber?: string,
        companyName?: string
    }>
}

const userSlice = createSlice({
    name: 'user',
    initialState: {
        firstName: '',
        secondName: '',
        login: '',
        email: '',
        languageType: 'PL',
        accessLevels: [],
        etag: '',
        version: 0
    } as IUserSliceState,
    reducers: {
        setUser: (state: IUserSliceState, {payload}: PayloadAction<IUserSliceState>) => payload
    }
})

export const {setUser} = userSlice.actions

export default userSlice.reducer