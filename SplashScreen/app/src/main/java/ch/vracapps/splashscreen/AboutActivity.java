package ch.vracapps.splashscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.vracapps.splashscreen.model.About_Classes.About;
import ch.vracapps.splashscreen.model.About_Classes.AboutCategory;
import iammert.com.expandablelib.ExpandCollapseListener;
import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_main);

        ExpandableLayout layout = (ExpandableLayout)findViewById(R.id.expandable_layout);


        layout.setRenderer(new ExpandableLayout.Renderer<AboutCategory, About>() {

            @Override
            public void renderParent(View view, AboutCategory aboutCategory, boolean isExpand, int parentPosition) {
                ((TextView)view.findViewById(R.id.tv_parent_name)).setText(aboutCategory.name);
                ((ImageView)view.findViewById(R.id.arrow)).setImageResource(R.drawable.ic_arrow_up);
            }

            @Override
            public void renderChild(View view, About about, int parentPosition, int childPosition) {
                ((TextView)view.findViewById(R.id.tv_child_name)).setText(about.name);

            }
        });

        layout.setExpandListener(new ExpandCollapseListener.ExpandListener<AboutCategory>() {
            @Override
            public void onExpanded(int i, AboutCategory aboutCategory, View view) {
                //layout expanded
                ((ImageView)view.findViewById(R.id.arrow)).setImageResource(R.drawable.ic_arrow_down);
            }
        });

        layout.setCollapseListener(new ExpandCollapseListener.CollapseListener<AboutCategory>() {

            @Override
            public void onCollapsed(int i, AboutCategory aboutCategory, View view) {
                //layout collapsed
                ((ImageView)view.findViewById(R.id.arrow)).setImageResource(R.drawable.ic_arrow_up);
            }
        });

        layout.addSection(getSection());
        layout.addSection(getSection());
        layout.addSection(getSection());
    }

    private Section<AboutCategory,About> getSection() {
        Section<AboutCategory,About> section = new Section<>();
        AboutCategory aboutCategory = new AboutCategory("Quest-ce que Vracapps ?");

        List<About> listAbout = new ArrayList<>();
        //Example
        for(int i=0;i<10;i++){
            listAbout.add(new About("About row "+i));
        }
        section.parent = aboutCategory;
        section.children = listAbout;

        return section;

    }
}
