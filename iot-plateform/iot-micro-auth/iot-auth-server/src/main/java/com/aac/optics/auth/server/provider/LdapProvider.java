package com.aac.optics.auth.server.provider;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

public class LdapProvider {
    public static String GetADInfo(String url, String name, String pwd) {
        String result = "";
        Hashtable HashEnv = new Hashtable();
        HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别
        HashEnv.put(Context.SECURITY_PRINCIPAL, "aacoptics\\" + name); // AD User
        HashEnv.put(Context.SECURITY_CREDENTIALS, pwd); // AD Password
        HashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
        HashEnv.put(Context.PROVIDER_URL, url);
        try {
            LdapContext ctx = new InitialLdapContext(HashEnv, null);
            // 域节点
            String searchBase = "DC=aacoptics,DC=com";
            // LDAP搜索过滤器类
            String searchFilter = "SAMAccountName=" + name;
            // 搜索控制器
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            // 根据设置的域节点、过滤器类和搜索控制器搜索LDAP得到结果
            NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {// 遍历结果集
                SearchResult sr = (SearchResult) answer.next();// 得到符合搜索条件的DN
                result = sr.getName();
            }
            ctx.close();
        } catch (NamingException e) {
            return null;
        }
        return result;
    }
}
