import {useTranslation} from 'react-i18next'
import React, {useState} from "react";
import ListClient from "./moderator/ListClient";

import SettingsIcon from '@material-ui/icons/SettingsRounded'
import AccountsListIcon from '@material-ui/icons/PeopleAltRounded'

import {useSelector} from "react-redux";
import {selectDarkMode} from "../../redux/slices/userSlice";
import ChangeEmail from "../../components/changeData/ChangeEmail";
import ChangePassword from "../../components/changeData/ChangePassword";
import ChangeModeratorData from "../../components/changeData/ChangeModeratorData";
import ManageWorkers from "./moderator/ManageBusinessWorkers";
import PanelLayout from "../../layouts/PanelLayout";


export default function ModeratorPanel() {
    const {t} = useTranslation()
    const [listClient, setListClient] = useState(true)
    const handleListClient = () => {
        setListClient(state => !state)
        setManage(true)
    }

    const darkModel = useSelector(selectDarkMode)

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
                    light: 'pink-dark',
                    dark: 'white'
            }}
            menu={[
                {
                    link: '/accounts',
                    text: t('list accounts'),
                    Icon: AccountsListIcon,
                    Component: ListClient
                },
                {
                    link: '/ManageWorkers',
                    text: t('Manage business workers'),
                    Icon: AccountsListIcon,
                    Component: ManageWorkers
                },
                {
                    link: '/settings',
                    text: t('settings'),
                    Icon: SettingsIcon,
                    Component: () => (
                        <>
                            <ChangeModeratorData
                                open={isDataEdit}
                                onOpen={handleIsDataEdit}
                                onConfirm={() => {setIsDataEdit(false)}}
                                onCancel={() => {setIsDataEdit(false)}}
                            />
                            <ChangeEmail
                                open={isEmailEdit}
                                onOpen={handleIsEmailEdit}
                                onConfirm={() => {setIsEmailEdit(false)}}
                                onCancel={() => {setIsEmailEdit(false)}}
                            />
                            <ChangePassword
                                open={isPasswordEdit}
                                onOpen={handleIsPasswordEdit}
                                onConfirm={() => {setIsPasswordEdit(false)}}
                                onCancel={() => {setIsPasswordEdit(false)}}
                            />
                        </>
                    )
                }
            ]}
        />
    )
}