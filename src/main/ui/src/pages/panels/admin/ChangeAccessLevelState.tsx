import React, {useReducer} from "react";
import {useTranslation} from 'react-i18next'
import RoundedButton from '../../../components/RoundedButton'
import axios from "axios";
import styles from '../../../styles/ManageAccount.module.css'
import Grid from "@material-ui/core/Grid";


export default function ChangeAccessLevelState() {
    const [, forceUpdate] = useReducer(x => x + 1, 0); // used to force component refresh on forceUpdate call
    const {t} = useTranslation()

    const currentAccount = JSON.parse(sessionStorage.getItem("changeAccessLevelStateAccount") as string)

    const handleChangeAccessLevelState = async (accessLevel: string, enabled: boolean) => {
        const json = {
            accountLogin: currentAccount.login,
            accessLevel: accessLevel,
            accountVersion: currentAccount.version,
            enabled: enabled
        }

        await fetch("http://localhost:8080/api/account/change-access-level-state", {
            method: "PUT",
            mode: "same-origin",
            body: JSON.stringify(json),
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "If-Match": currentAccount.etag
            }
        })

        const result = await axios.get(`http://localhost:8080/api/account/details/${currentAccount.login}`);
        sessionStorage.setItem("changeAccessLevelStateAccount", JSON.stringify(result.data));
        forceUpdate()
    }

    return (
        <Grid container className={styles.wrapper}>
            <Grid item style={{display: "block"}} className={styles.item}>
                {currentAccount ? currentAccount.accessLevels.map((accessLevel: any) => (
                    <div>
                        <h4>{accessLevel.accessLevelType}</h4>
                        <RoundedButton color="blue"
                                       onClick={() => handleChangeAccessLevelState(accessLevel.accessLevelType, !accessLevel.enabled)}
                        >{accessLevel.enabled ? t("disable") : t("enable")}
                        </RoundedButton>
                        <br/>
                    </div>
                )) : <RoundedButton color={"blue"} onClick={forceUpdate}>{t("refresh")}</RoundedButton>}
            </Grid>
        </Grid>
    )
}