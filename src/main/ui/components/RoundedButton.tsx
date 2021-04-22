import Button from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/styles'
import * as React from 'react'
export interface RoundedButtonProps {
    readonly className?: string,
    readonly color: "pink" | "yellow" | "green" | "blue"
    readonly children?: any,
    readonly style?: React.CSSProperties 
}

export default function RoundedButton(props: RoundedButtonProps) {
    
    const {
        className: styles,
        children,
        color,
        style
    } = props

    const classes = makeStyles(theme => ({
        root: {
            fontFamily: "'Montserrat', sans-serif",
            backgroundColor: 'var(--' + color + ')',
            color: 'var(--white)',
            borderRadius: 34,
            '&:hover': {
                backgroundColor: 'var(--' + color + '-dark)'
            }
        }
    }))()

    return (
        <Button 
            className={(styles || '') + ' ' + classes.root}
            style={style || null}
        >{children}</Button>
    )
}