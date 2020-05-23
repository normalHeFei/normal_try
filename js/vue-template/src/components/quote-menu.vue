<template>
    <ul>
        <li><a href="#" v-on:click.prevent="queryReqLog">现券-对话</a></li>
        <li><a href="#">现券-请求</a></li>
        <li><a href="#">质押式-对话</a></li>
    </ul>
</template>

<script>
    export default {
        name: "quote-menu",
        methods: {
            queryReqLog() {

            }
        }
    }
</script>

<style scoped>

    ul {
        list-style: none;
    }

    ul li {
        margin-top: 10px;
    }

    ul li a {
        text-decoration: none;
    }
</style>
