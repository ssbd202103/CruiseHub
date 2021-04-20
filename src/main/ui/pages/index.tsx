import Link from 'next/link'


export default function Home() {
    return (
        <div>
            <h1>Hello, Next.js with Redux!</h1>
            <Link href="/signin">Go to login page</Link> 
            <Link href="/signup/client">Go to client regestration page</Link>
        </div>
    )
}
