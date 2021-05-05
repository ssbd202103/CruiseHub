import {Checkbox, FormControlLabel, FormGroup, Box, RadioGroup, Radio} from "@material-ui/core";
import {Link} from 'react-router-dom';
import {useHistory, useLocation} from 'react-router-dom';
import React from "react";
import {useTranslation} from 'react-i18next'
import RoundedButton from '../../../components/RoundedButton'
import axios from "axios";
import {inspect} from "util";
import styles from '../../../styles/ManageAccount.module.css'
import Grid from "@material-ui/core/Grid";


export default function ChangeAccessLevelState() {
    const {t} = useTranslation()

    const currentAccount = JSON.parse(sessionStorage.getItem("changeAccessLevelStateAccount") as string)


    // const handleConfirm = async () => {
    //     if (!["Moderator", "Administrator"].includes(selectedAccessLevel)) return;
    //
    //     const json = {
    //         accountLogin: currentAccount.login,
    //         accessLevel: selectedAccessLevel.toUpperCase(),
    //         accountVersion: currentAccount.version
    //     }
    //
    //     // await axios.put("http://localhost:8080/api/account/grant-access-level", {
    //     //     json,
    //     //     mode: 'no-cors',
    //     //     headers: {
    //     //         "Content-Type": "application/json",
    //     //         "Accept": "application/json",
    //     //         "If-Match": currentAccount.etag
    //     //     }
    //     // })
    //
    //     /// above axios code java backend does not see set headers, most likely CORS issue,
    //     //  no-cors mode does not resolve issue for axios, it works with fetch though
    //
    //     await fetch("http://localhost:8080/api/account/grant-access-level", {
    //         method: "PUT",
    //         mode: "same-origin",
    //         body: JSON.stringify(json),
    //         headers: {
    //             "Content-Type": "application/json",
    //             "Accept": "application/json",
    //             "If-Match": currentAccount.etag
    //         }
    //     })
    // }
    // const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    //     setSelectedAccessLevel(event.target.value)
    // };
    console.log(currentAccount.accessLevels)

    const handleChangeAccessLevelState = async (accessLevel: string, enabled: boolean) => {
        const json = {
            accountLogin: currentAccount.login,
            accessLevel: accessLevel,
            accountVersion: currentAccount.version,
            enabled: enabled
        }
        console.log('json:')
        console.log(json)

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

    }

    return (
        <Grid container className={styles.wrapper}>
            <Grid item style={{display: "block"}} className={styles.item}>
                {currentAccount.accessLevels.map((accessLevel: any) => (
                    <div>
                        <h4>{accessLevel.accessLevelType}</h4>
                        <RoundedButton color="blue"
                                       onClick={() => handleChangeAccessLevelState(accessLevel.accessLevelType, !accessLevel.enabled)}
                        >{accessLevel.enabled ? "disable" : "enable"}
                        </RoundedButton>
                        <br/>
                    </div>
                ))}
                {/*{currentAccount.accessLevels.filter}*/}
                {/*<h3>{t("address")}</h3>*/}
                {/*<div>*/}
                {/*    <RoundedButton color="blue"*/}
                {/*                   onClick={handleChangAddress}*/}
                {/*    >{t("address change btn")}</RoundedButton>*/}
                {/*</div>*/}
            </Grid>
        </Grid>
    )
}