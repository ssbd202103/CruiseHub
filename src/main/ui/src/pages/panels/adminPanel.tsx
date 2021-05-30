import {useTranslation} from 'react-i18next'
import React, {useState} from "react";
import ListClient from "./admin/ListClient";

import SettingsIcon from '@material-ui/icons/SettingsRounded'
import AccountsListIcon from '@material-ui/icons/PeopleAltRounded'

import ChangeAccountData from "./admin/ChangeAccountData"
import GrantAccessLevel from "./admin/GrantAccessLevel"
import ChangeAccessLevelState from "./admin/ChangeAccessLevelState"

import {selectDarkMode} from "../../redux/slices/userSlice";
import {useSelector} from "react-redux";
import ChangeEmail from "../../components/changeData/ChangeEmail";
import ChangePassword from "../../components/changeData/ChangePassword";
import ChangeAdministratorData from "../../components/changeData/ChangeAdministratorData";
import RequestSomeonePasswordReset from "../reset/requestSomeonesPasswordReset";
import PanelLayout from "../../layouts/PanelLayout";

export default function AdminPanel() {
    const {t} = useTranslation()
    const [listClient, setListClient] = useState(true)
    const handleListClient = () => {
        setListClient(state => !state)
        setManage(true)
    }


    const [manageAccount, setManage] = useState(true)
    const handleManageAccount = () => {
        setManage(state => !state)
        setListClient(true)
    }

    const [isEmailEdit, setIsEmailEdit] = useState(false)
    const [isDataEdit, setIsDataEdit] = useState(false)
    const [isPasswordEdit, setIsPasswordEdit] = useState(false)

    const handleIsEmailEdit = () => {
        setIsEmailEdit(true)
        setIsDataEdit(false)
        setIsPasswordEdit(false)
    }

    const handleIsDataEdit = () => {
        setIsDataEdit(true)
        setIsEmailEdit(false)
        setIsPasswordEdit(false)
    }

    const handleIsPasswordEdit = () => {
        setIsPasswordEdit(true)
        setIsDataEdit(false)
        setIsEmailEdit(false)
    }

    return (
        <PanelLayout
            color={{
                light: 'yellow-dark',
                dark: 'white-light'
            }}
            menu={[
                {
                    link: '/accounts',
                    Icon: AccountsListIcon,
                    text: t('list accounts'),
                    Component: ListClient
                },
                {
                    link: '/settings',
                    Icon: SettingsIcon,
                    text: t('settings'),
                    Component: () => (
                        <>
                            <ChangeAdministratorData
                                open={isDataEdit}
                                onOpen={handleIsDataEdit}
                                onConfirm={() => {setIsDataEdit(false)}}
                                onCancel={() => {setIsDataEdit(false)}} />
                            <ChangeEmail
                                open={isEmailEdit}
                                onOpen={handleIsEmailEdit}
                                onConfirm={() => {setIsEmailEdit(false)}}
                                onCancel={() => {setIsEmailEdit(false)}} />
                            <ChangePassword
                                open={isPasswordEdit}
                                onOpen={handleIsPasswordEdit}
                                onConfirm={() => {setIsPasswordEdit(false)}}
                                onCancel={() => {setIsPasswordEdit(false)}} />
                        </>
                    )
                }
            ]}
            otherRoutes={[
                {
                    to: '/accounts/change_account_data',
                    Component: ChangeAccountData
                },
                {
                    to: '/accounts/grant_access_level',
                    Component: GrantAccessLevel
                },
                {
                    to: '/accounts/change_access_level_state',
                    Component: ChangeAccessLevelState
                },
                {
                    to: '/accounts/resetSomebodyPassword',
                    Component: RequestSomeonePasswordReset
                }
            ]}
        />
    )
}