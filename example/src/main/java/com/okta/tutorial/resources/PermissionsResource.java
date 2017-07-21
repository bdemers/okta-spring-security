package com.okta.tutorial.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("/permissions")
public class PermissionsResource {


    @GetMapping
    public String[] getPermissions() {
//            @Context HttpServletRequest request) {

//        Set<String> permissions = new HashSet<>();
//
//        GroupCustomDataPermissionResolver groupPermissionResolver = new GroupCustomDataPermissionResolver();
//        AccountCustomDataPermissionResolver accountPermissionResolver = new AccountCustomDataPermissionResolver();
//
//        Account account = AccountResolver.INSTANCE.getAccount(request);
//        if(account != null) {
//
//            account.getGroups().forEach(group ->
//                    groupPermissionResolver.resolvePermissions(group).forEach(perm ->
//                            permissions.add(perm.toString())));
//
//            accountPermissionResolver.resolvePermissions(account).forEach(perm -> permissions.add(perm.toString()));
//        }
//
//        return permissions.stream().toArray(String[]::new);
        return null;
    }

}
