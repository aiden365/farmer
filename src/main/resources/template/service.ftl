package ${packageName};

${(basePackageAllName??)?string("import ${basePackageAllName?if_exists};","")}

/**
 * ${annotation}
 * @author ${author}
 * @version 1.0
 * @data ${createDate}
 **/
public interface ${className} ${(baseClassName??)?string("extends ${baseClassName?if_exists}<${pojoName}>","")}{


}
