<template>
    <div class="n-listitem">
        <h3 class="n-title">{{item.title}}</h3>
        <p class="n-content">{{item.content}}</p>
        <span class="n-label" v-for="label in item.labels" :key="label">{{label}}</span>
    </div>
</template>

<script>
    export default {
        name: "ListItem",
        props: {
            item: Object
        }
    };
</script>

<style scoped>

</style>
