<template>
    <div class="middle">
        <Sidebar :users="users" :posts="posts"/>
        <main>
            <Index :users="users" :posts="posts" v-if="page === 'Index'"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <AddPost v-if="page === 'AddPost'"/>
            <EditPost v-if="page === 'EditPost'"/>
            <Users :users="users" v-if="page === 'Users'"/>
            <Post :post="post" :users="users" v-if="page == 'Post'"/>
        </main>
    </div>
</template>
<script>
    import Index from './middle/Index';
    import Enter from './middle/Enter';
    import Register from './middle/Register';
    import AddPost from './middle/AddPost';
    import EditPost from "./middle/EditPost";
    import Sidebar from './middle/Sidebar'
    import Users from './middle/Users'
    import Post from './middle/Post'

    export default {
        name: "Middle",
        props: ['users', 'posts'],
        data: function () {
            return {
                page: "Index",
                post: undefined
            }
        },
        components: {
            Sidebar,
            EditPost,
            Index,
            Enter,
            Register,
            AddPost,
            Users,
            Post
        }, beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
            this.$root.$on("onPost", (id) => {
                this.page = "Post";
                this.post = this.posts[id];
            })
        }
    }
</script>

<style scoped>

</style>
