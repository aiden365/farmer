package ${packageName};

${basePackageAllName?if_exists?string("import ${basePackageAllName};","")}

/**
 * ${annotation}
 * @author ${author}
 * @version 1.0
 * @data ${createDate}
 **/
public class ${className} ${baseClassName?if_exists?string("${baseClassName}<${primaryKeyType}>","")}{


}
