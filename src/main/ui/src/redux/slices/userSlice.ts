import {createSelector, createSlice, PayloadAction} from '@reduxjs/toolkit'

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
        setUser: (state: IUserSliceState, {payload}: PayloadAction<IUserSliceState>) => payload,
        changeEmail: (state: IUserSliceState, {payload}: PayloadAction<string>) => {
            state.email = payload
        }
    }
})

export const {setUser, changeEmail} = userSlice.actions

const selectSelf = (state: { user: IUserSliceState }) => state

export const selectEmail = createSelector(selectSelf, state => state.user.email)
export const selectEtag = createSelector(selectSelf, state => state.user.etag)
export const selectLogin = createSelector(selectSelf, state => state.user.login)
export const selectVersion = createSelector(selectSelf, state => state.user.version)

export default userSlice.reducer