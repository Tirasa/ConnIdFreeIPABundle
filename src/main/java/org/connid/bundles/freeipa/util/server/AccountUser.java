/**
 * ====================
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008-2009 Sun Microsystems, Inc. All rights reserved.
 * Copyright 2011-2013 Tirasa. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License("CDDL") (the "License"). You may not use this file
 * except in compliance with the License.
 *
 * You can obtain a copy of the License at https://oss.oracle.com/licenses/CDDL
 * See the License for the specific language governing permissions and limitations
 * under the License.
 *
 * When distributing the Covered Code, include this CDDL Header Notice in each file
 * and include the License file at https://oss.oracle.com/licenses/CDDL.
 * If applicable, add the following below this CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * ====================
 */
package org.connid.bundles.freeipa.util.server;

import com.unboundid.ldap.sdk.AddRequest;
import com.unboundid.ldap.sdk.Attribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountUser {

    private final String dn;

    private final List<String> objectClasses;

    private final String userPassword;

    private final String cn;

    private final String displayName;

    private final String loginShell;

    private final String gecos;

    private final String gidNumber;

    private final String uidNumber;

    private final String homeDirectory;

    private final String uid;

    private final List<String> memberOf;

    private final String sn;

    private final String mepManagedEntry;

    private final String givenName;

    private final String initials;

    private String mail;

    private String krbPrincipalName;

    public AccountUser(final String uid, final String password,
            final String givenName, final String sn, final String posixIDsNumber,
            final List<String> memberOf) {
        dn = "uid=" + uid + ",cn=users,cn=accounts,dc=tirasa,dc=net";
        displayName = givenName + sn;
        cn = givenName + sn;
        objectClasses = DefaultObjectClasses.toList();
        loginShell = "/bin/sh";
        userPassword = password;
        gecos = givenName + sn;
        gidNumber = posixIDsNumber;
        uidNumber = posixIDsNumber;
        homeDirectory = "/home/" + uid;
        this.uid = uid;
        this.givenName = givenName;
        this.sn = sn;
        initials = givenName.substring(0, 1) + sn.substring(0, 1);
        mepManagedEntry = "cn=" + uid + ",cn=groups,cn=accounts,dc=tirasa,dc=net";
        this.memberOf = new ArrayList<String>();
        this.memberOf.add("cn=ipausers,cn=groups,cn=accounts,dc=tirasa,dc=net");
        if (memberOf != null) {
            this.memberOf.addAll(memberOf);
        }
    }

    public AccountUser setMail(final String mail) {
        this.mail = mail;
        return this;
    }

    public AccountUser setKrbPrincipalName(final String krbPrincipalName) {
        this.krbPrincipalName = krbPrincipalName;
        return this;
    }

    public String getDn() {
        return dn;
    }

    public List<String> getObjectClasses() {
        return objectClasses;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getCn() {
        return cn;
    }

    public String getLoginShell() {
        return loginShell;
    }

    public String getGecos() {
        return gecos;
    }

    public String getGidNumber() {
        return gidNumber;
    }

    public String getUidNumber() {
        return uidNumber;
    }

    public String getSn() {
        return sn;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public String getUid() {
        return uid;
    }

    public String getMail() {
        return mail;
    }

    public String getKrbPrincipalName() {
        return krbPrincipalName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getInitials() {
        return initials;
    }

    public List<String> getMemberOf() {
        return memberOf;
    }

    private enum DefaultObjectClasses {

        TOP("top"),
        PERSON("person"),
        ORGANIZATIONAL_PERSON("organizationalperson"),
        INET_ORG_PERSON("inetorgperson"),
        INET_USER("inetuser"),
        POSIX_ACCOUNT("posixAccount"),
        KRB_PRINCIPAL_AUX("krbprincipalaux"),
        KRB_TICKET_POLICY_AUX("krbticketpolicyaux"),
        IPAOBJECT("ipaobject"),
        IPA_SSH_USER("ipasshuser"),
        IPA_SSH_GROUP_OF_PUB_KEYS("ipaSshGroupOfPubKeys"),
        MEP_ORIGIN_ENTRY("mepOriginEntry");

        private final String ldapName;

        private DefaultObjectClasses(final String ldapName) {
            this.ldapName = ldapName;
        }

        @Override
        public String toString() {
            return ldapName;
        }

        public static List<String> toList() {
            final List<String> defaultObjectClass = new ArrayList<String>();
            for (final DefaultObjectClasses objectClass : values()) {
                defaultObjectClass.add(objectClass.toString());
            }
            return defaultObjectClass;
        }
    }

    public enum DefaultAttributes {

        OBJECT_CLASS("objectClass"),
        DN("dn"),
        USER_PASSWORD("userPassword"),
        CN("cn"),
        DISPLAY_NAME("displayName"),
        UID("uid"),
        GECOS("gecos"),
        MEP_MANAGED_ENTRY("mepManagedEntry"),
        UID_NUMBER("uidNumber"),
        GID_NUMBER("gidNumber"),
        LOGIN_SHELL("loginShell"),
        HOME_DIRECTORY("homeDirectory"),
        MEMBER_OF("memberOf"),
        SN("sn"),
        MAIL("mail"),
        KRB_PRINCIPAL_NAME("krbPrincipalName"),
        GIVEN_NAME("givenName"),
        INITIALS("initials");

        private final String ldapName;

        private DefaultAttributes(final String ldapName) {
            this.ldapName = ldapName;
        }

        public String ldapValue() {
            return ldapName;
        }
    }

    public AddRequest toAddRequest() {

        final Attribute oc = new Attribute(DefaultAttributes.OBJECT_CLASS.ldapValue(), objectClasses);
        final Attribute userpassword = new Attribute(DefaultAttributes.USER_PASSWORD.ldapValue(), this.userPassword);
        final Attribute commonName = new Attribute(DefaultAttributes.CN.ldapValue(), this.cn);
        final Attribute displayname = new Attribute(DefaultAttributes.DISPLAY_NAME.ldapValue(), this.displayName);
        final Attribute uID = new Attribute(DefaultAttributes.UID.ldapValue(), this.uid);
        final Attribute gecOS = new Attribute(DefaultAttributes.GECOS.ldapValue(), this.gecos);
        final Attribute mepmanagedentry = new Attribute(DefaultAttributes.MEP_MANAGED_ENTRY.ldapValue(),
                this.mepManagedEntry);
        final Attribute uidnumber = new Attribute(DefaultAttributes.UID_NUMBER.ldapValue(), this.uidNumber);
        final Attribute gidnumber = new Attribute(DefaultAttributes.GID_NUMBER.ldapValue(), this.gidNumber);
        final Attribute loginshell = new Attribute(DefaultAttributes.LOGIN_SHELL.ldapValue(), this.loginShell);
        final Attribute homedirectory = new Attribute(DefaultAttributes.HOME_DIRECTORY.ldapValue(), this.homeDirectory);
        final Attribute memberof = new Attribute(DefaultAttributes.MEMBER_OF.ldapValue(), this.memberOf);
        final Attribute surname = new Attribute(DefaultAttributes.SN.ldapValue(), this.sn);
        final Attribute email = new Attribute(DefaultAttributes.MAIL.ldapValue(), this.mail);
        final Attribute krbprincipalname = new Attribute(DefaultAttributes.KRB_PRINCIPAL_NAME.ldapValue(),
                this.krbPrincipalName);
        final Attribute givenname = new Attribute(DefaultAttributes.GIVEN_NAME.ldapValue(), this.givenName);
        final Attribute userInitials = new Attribute(DefaultAttributes.INITIALS.ldapValue(), this.initials);

        final Collection<Attribute> attributes = new ArrayList();
        attributes.add(oc);
        attributes.add(commonName);
        attributes.add(displayname);
        attributes.add(mepmanagedentry);
        attributes.add(uID);
        attributes.add(gecOS);
        attributes.add(uidnumber);
        attributes.add(gidnumber);
        attributes.add(loginshell);
        attributes.add(homedirectory);
        attributes.add(userpassword);
        attributes.add(memberof);
        attributes.add(surname);
        attributes.add(email);
        attributes.add(krbprincipalname);
        attributes.add(givenname);
        attributes.add(userInitials);

        return new AddRequest(dn, attributes);
    }

}
