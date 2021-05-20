import * as React from 'react'
import {FormControl, InputLabel, MenuItem, Select} from '@material-ui/core'
import {makeStyles} from '@material-ui/styles'


const useStyles = makeStyles(theme => ({
    root: {
        '& .MuiSelect-root': {
            fontFamily: "'Montserrat Alternates', sans-serif",
            fontSize: '1.2rem'
        },
        '& .MuiInputBase-root': {
            borderColor: 'var(--dark)',
            '&.Mui-focused.Mui-focused fieldset': {
                borderColor: 'var(--dark)'
            }
        },
        '& .MuiInputLabel-root': {
            fontFamily: "'Montserrat', sans-serif",
            fontSize: '1.1rem',
            color: 'var(--dark)'
        },
        '& .MuiInputLabel-root.Mui-focused': {
            color: 'var(--dark) !important'
        },

    }
}))

const inputStyle = {}

const labelStyle = {
    style: {}
}

export interface DarkedTextFieldProps {
    readonly className?: string,
    readonly label?: string,
    readonly style?: React.CSSProperties,
    readonly options: Array<string | number>,

    readonly  onSelectedChange?: any
}

export default function DarkedTextField(props: any) {

    const classes = useStyles()

    const {
        className: styles,
        label,
        style,
        options,
        onSelectedChange
    } = props

    return (
        <FormControl
            className={classes.root + ' ' + (styles || "")}
            style={style || null}
            variant="outlined"
        >
            <InputLabel>{label}</InputLabel>
            <Select
                label={label + 'a'}
            >
                {options.map((item: string | number, index: number) => <MenuItem
                    key={item} value={item}
                    onClick={() => {
                        onSelectedChange(item)
                    }
                    }
                >{item}</MenuItem>)}
            </Select>
        </FormControl>


    )
}
