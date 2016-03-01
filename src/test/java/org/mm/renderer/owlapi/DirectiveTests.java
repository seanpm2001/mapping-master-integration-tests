package org.mm.renderer.owlapi;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mm.renderer.owlapi.AllRenderingTestSuite.DirectiveTest;

@RunWith(Categories.class)
@IncludeCategory(DirectiveTest.class)
@SuiteClasses({ BasicTest.class, ReferenceTest.class })
public class DirectiveTests
{
   // NO-OP
}
